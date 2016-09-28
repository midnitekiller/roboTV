package org.xvdr.extractor;

import com.google.android.exoplayer2.extractor.ExtractorInput;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ExtractorBufferPacket implements BufferPacket {


    final ByteBuffer buffer;

    public ExtractorBufferPacket(byte[] data) {
        buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.BIG_ENDIAN);
    }

    public byte[] data() {
        return buffer.array();
    }

    public void rewind() {
        buffer.clear();
    }

    public int position() {
        return buffer.position();
    }

    public void read(ExtractorInput input, int length) throws IOException, InterruptedException {
        input.readFully(data(), 0, length);
        rewind();
    }

    public void peek(ExtractorInput input, int length) throws IOException, InterruptedException {
        input.peekFully(data(), 0, length);
        rewind();
    }

    public char getU8() {
        return (char)buffer.get();
    }

    public byte getS8() {
        return buffer.get();
    }

    public int getU16() {
        return buffer.getShort();
    }

    public short getS16() {
        return buffer.getShort();
    }

    public long getU32() {
        return buffer.getInt();
    }

    public int getS32() {
        return buffer.getInt();
    }

    public long getU64() {
        return buffer.getLong();
    }

    public long getS64() {
        return buffer.getLong();
    }

    public String getString() {
        String string = nativeString(buffer.array(), buffer.position(), buffer.remaining());
        int size = nativeSize(buffer.array(), buffer.position(), buffer.remaining());

        buffer.position(buffer.position() + size);
        return string;
    }

    public void readBuffer(byte[] buffer, int offset, int length) {
        this.buffer.get(buffer, offset, length);
    }

    private native String nativeString(byte[] buffer, int offset, int length);

    private native int nativeSize(byte[] buffer, int offset, int length);
}