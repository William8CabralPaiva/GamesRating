package com.cabral.arch

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun rememberReorderableState(
    onMove: (Int, Int) -> Unit,
    onDragEnd: () -> Unit = {}
): ReorderableState {
    val listState = rememberLazyListState()
    return remember(listState) {
        ReorderableState(listState, onMove, onDragEnd)
    }
}

class ReorderableState(
    val listState: LazyListState,
    private val onMove: (Int, Int) -> Unit,
    private val onDragEnd: () -> Unit
) {
    var draggedItemIndex by mutableStateOf<Int?>(null)
    var draggingOffset by mutableFloatStateOf(0f)

    fun isDragging(index: Int) = index == draggedItemIndex

    suspend fun detectReorder(
        pointerInputScope: androidx.compose.ui.input.pointer.PointerInputScope,
        index: Int
    ) {
        with(pointerInputScope) {
            detectDragGesturesAfterLongPress(
                onDragStart = {
                    draggedItemIndex = index
                },
                onDragEnd = {
                    draggedItemIndex = null
                    draggingOffset = 0f
                    onDragEnd()
                },
                onDragCancel = {
                    draggedItemIndex = null
                    draggingOffset = 0f
                },
                onDrag = { change, dragAmount ->
                    change.consume()
                    draggingOffset += dragAmount.y

                    val currentItemInfo = listState.layoutInfo.visibleItemsInfo
                        .find { it.index == draggedItemIndex } ?: return@detectDragGesturesAfterLongPress

                    val dragPosition = currentItemInfo.offset + draggingOffset + (currentItemInfo.size / 2)

                    val targetItem = listState.layoutInfo.visibleItemsInfo
                        .find { item ->
                            dragPosition > item.offset && dragPosition < (item.offset + item.size) && item.index != draggedItemIndex
                        }

                    if (targetItem != null) {
                        val currentIndex = draggedItemIndex!!
                        onMove(currentIndex, targetItem.index)
                        draggingOffset += (currentItemInfo.offset - targetItem.offset)
                        draggedItemIndex = targetItem.index
                    }
                }
            )
        }
    }
}

fun Modifier.reorderableItem(
    state: ReorderableState,
    index: Int
): Modifier = this.then(
    Modifier
        .zIndex(if (state.isDragging(index)) 1f else 0f)
        .graphicsLayer {
            val isDragging = state.isDragging(index)
            translationY = if (isDragging) state.draggingOffset else 0f
        }
        .pointerInput(state, index) {
            state.detectReorder(this, index)
        }
)
