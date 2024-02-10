package com.valrod.utils;

public class ColorUtils {

	public static int setAlpha(int color, int newAlpha) {
		return (newAlpha << 24) | (color & 0x00FFFFFF);
	}
	
}
