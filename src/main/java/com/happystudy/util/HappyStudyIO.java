package com.happystudy.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HappyStudyIO {
	public static void write(Object obj, String address) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(address));
			oos.writeObject(obj);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object read(String address) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(address));
			Object result =  ois.readObject();
			ois.close();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
