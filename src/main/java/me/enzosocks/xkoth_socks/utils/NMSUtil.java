package me.enzosocks.xkoth_socks.utils;

import org.bukkit.Bukkit;

public class NMSUtil {
	public static double version = getNMSVersion();
	public static double getNMSVersion() {
		String var1 = Bukkit.getServer().getClass().getPackage().getName();
		String[] arrayOfString = var1.replace(".", ",").split(",")[3].split("_");
		String var2 = arrayOfString[0].replace("v", "");
		String var3 = arrayOfString[1];
		return Double.parseDouble(var2 + "." + var3);
	}

	public static boolean supportsHexColor() {
		return version >= 1.16;
	}
}
