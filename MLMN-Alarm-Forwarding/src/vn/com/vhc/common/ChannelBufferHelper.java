package vn.com.vhc.common;


import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class ChannelBufferHelper {

	private static final Charset US_ASCII = Charset.forName("US-ASCII");
	
	/**
	 * Convert from {@link ChannelBuffer} to {@link String} type.
	 */
	public static String toString(ChannelBuffer buff) {
		return new String(buff.array());
	}
	
	/**
	 * Convert from {@link String} to {@link ChannelBuffer} type.
	 */
	public static ChannelBuffer fromString(String s) {
		return ChannelBuffers.wrappedBuffer(s
				.getBytes(US_ASCII));
	}
	
	/**
	 * Returns the number of bytes between the readerIndex of the haystack and
	 * the first needle found in the haystack. -1 is returned if no needle is
	 * found in the haystack.
	 */
	public static int indexOf(ChannelBuffer haystack, ChannelBuffer needle) {
		for (int i = haystack.readerIndex(); i < haystack.writerIndex(); i++) {
			int haystackIndex = i;
			int needleIndex;
			for (needleIndex = 0; needleIndex < needle.capacity(); needleIndex++) {
				if (haystack.getByte(haystackIndex) != needle
						.getByte(needleIndex)) {
					break;
				} else {
					haystackIndex++;
					if (haystackIndex == haystack.writerIndex()
							&& needleIndex != needle.capacity() - 1) {
						return -1;
					}
				}
			}

			if (needleIndex == needle.capacity()) {
				// Found the needle from the haystack!
				return i - haystack.readerIndex();
			}
		}
		return -1;
	}
}
