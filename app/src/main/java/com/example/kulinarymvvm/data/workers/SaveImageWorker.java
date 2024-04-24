package com.example.kulinarymvvm.data.workers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class SaveImageWorker extends Worker {
    Context context;
    public SaveImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String newFileName = getInputData().getString("image_filename");
        if(newFileName == null) {
            newFileName = UUID.randomUUID().toString() + ".jpg";
        }
        Uri foodImage = Uri.parse(getInputData().getString("image_uri"));
        File newImage = new File(context.getFilesDir(), newFileName);
        try {
            Bitmap loadedImage = Picasso.get().load(foodImage).get();
            newImage.createNewFile();
            ByteArrayOutputStream newImageStream = new ByteArrayOutputStream();
            loadedImage.compress(Bitmap.CompressFormat.JPEG, 95, newImageStream);
            byte[] imageByteData = newImageStream.toByteArray();
            FileOutputStream newImageFileStream = new FileOutputStream(newImage);
            newImageFileStream.write(imageByteData);
            newImageFileStream.flush();
            newImageFileStream.close();
        } catch (IOException e) {
            return Result.failure();
        }
        return Result.success();
    }
}
