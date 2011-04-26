package no.ntnu.stud.flatcraft.settings;

public enum Resolution {
	WVGA, QHD, WSVGA, HD, HDPLUS, FULLHD;

	public int getWidth(){
		switch(this){
		case WVGA: return 800;
		case QHD: return 960;
		case WSVGA: return 1024;
		case HD: return 1280;
		case HDPLUS: return 1600;
		case FULLHD: return 1920;
		default: return 1280;
		}
	}
	
	public int getHeight(){
		switch(this){
		case WVGA: return 480;
		case QHD: return 540;
		case WSVGA: return 576;
		case HD: return 720;
		case HDPLUS: return 900;
		case FULLHD: return 1080;
		default: return 720;
		}
	}
	
}
