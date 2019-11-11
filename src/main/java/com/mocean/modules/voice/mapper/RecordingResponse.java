package com.mocean.modules.voice.mapper;

public class RecordingResponse {
    private byte[] recordingBuffer;
    private String filename;

    public RecordingResponse(byte[] recordingBuffer, String filename) {
        this.recordingBuffer = recordingBuffer;
        this.filename = filename;
    }

    public byte[] getRecordingBuffer() {
        return recordingBuffer;
    }

    public String getFilename() {
        return filename;
    }
}
