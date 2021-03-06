/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.audio;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import org.lwjgl.system.MemoryUtil;

/**
 *
 * @author Javier
 */
public class AudioLoader {
    public static SoundData loadVorbis(String path){
        IntBuffer samplerate= MemoryUtil.memAllocInt(1);
        IntBuffer channels = MemoryUtil.memAllocInt(1);
        ShortBuffer buffer = stb_vorbis_decode_filename(path, channels, samplerate);
        
        SoundData data = new SoundData();
        data.channels = channels.get();
        data.samplerate = samplerate.get();
        data.format = data.channels == 2 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16;
        data.data = buffer;
        return data;
    }
}
