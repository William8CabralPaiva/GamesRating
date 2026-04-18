package com.cabral.meusjogosfavoritos.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(imageUrl: String, fileName: String): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            // 1. Download
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.connect()

            val input: InputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)
                ?: return@withContext Result.failure(Exception("Falha ao decodificar imagem"))

            // 2. Preparar MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/GamesRating")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            // 3. Salvar
            uri?.let { targetUri ->
                resolver.openOutputStream(targetUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(targetUri, contentValues, null, null)
                }
                Result.success(Unit)
            } ?: Result.failure(Exception("Erro ao criar URI no MediaStore"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}