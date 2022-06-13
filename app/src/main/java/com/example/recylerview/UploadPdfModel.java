package com.example.recylerview;

public class UploadPdfModel {

    String fileurl;
    String  filename;
    public UploadPdfModel(){

    }


    public UploadPdfModel(String fileurl, String filename) {
        this.fileurl = fileurl;
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}


