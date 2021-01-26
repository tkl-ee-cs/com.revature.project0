package com.project0.Bank;

import java.util.Scanner;

public class cleanScan {
	
	private static Scanner scanner = new Scanner(System.in); 
	
	public static String getStr() {
		String retString = "";
		while(retString.length() < 1) {
			try{
				retString = scanner.nextLine();
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(retString.length() < 1) {
				System.out.printf("...invalid input, try again...");
			}
		}
		return retString;
	}
	
	
	public static char getChar() {
		String retString = "";
		while(retString.length() != 1) {
			try{
				retString = scanner.nextLine();
			}catch (Exception e) {
				System.out.println("Not valid, try again.");
				e.printStackTrace();
			}
			if(retString.length() != 1) {
				System.out.printf("...invalid input, try again...");
			}
		}
		return retString.charAt(0);
	}
	
	public static int getInt() {
		String retString = "";
		while(!(retString.matches("[0-9]+"))) {
			try{
				retString = scanner.nextLine();
			}catch (Exception e) {
				System.out.println("Not valid input, try again.");
				e.printStackTrace();
			}
			if(!(retString.matches("[0-9]+"))) {
				System.out.printf("...invalid input, try again...");
			}
		}
		return Integer.parseInt(retString);
	}
}
