/* autogenerated by Processing revision 1281 on 2022-05-20 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class p4_CollatzFilter extends PApplet {

// FILE: CollatzFilter
// AUTHOR: Faizon Zaman
PImage source, img;
float dnsmp, dscale;
 public void setup(){
	// CANVAS SETTINGS
	/* size commented out by preprocessor */;
	/* pixelDensity commented out by preprocessor */;
	
	// GET IMAGE
	dnsmp = 1.00f; /* downsample factor => 1.00: least, +: most */
	// source = imagefit("./imgs/clouds.jpg", dnsmp);
	source = imagefit(randomImage(width/100,height/100), dnsmp); /* imagefit fits image to screen preserving aspect ratio */
	imageMode(CENTER);
	dscale = (dnsmp/2.f);
}

 public void draw(){
	collatzFilter(source); /* apply collatz to r,g,b channels */
	// meanCollatzFilter(source); /* apply collatz to rgb avg */
	image(source, width/2, height/2, source.pixelWidth*dscale, source.pixelHeight*dscale);
}

 public void collatzFilter(PImage srci) {
	srci.loadPixels();
	for (int i = 0; i < srci.pixelWidth; i++){
		for (int j = 0; j < srci.pixelHeight; j++){
			// GET CURRENT PIXEL COLOR
			int index = (i + j * srci.pixelWidth);
			index = constrain(index, 0, srci.pixels.length -1);
			int cpx = srci.pixels[index];
			
			// GET EACH CHANNEL
			int rpx = cpx >> 16 & 0xFF;
			int gpx = cpx >> 8 & 0xFF;
			int bpx = cpx & 0xFF;
			
			// APPLY COLLATZ TO EACH CHANNEL
			int nrpx = collatz(rpx);
			int ngpx = collatz(gpx);
			int nbpx = collatz(bpx);

			srci.pixels[index] = color(nrpx, ngpx, nbpx);
		}
	}
	srci.updatePixels();
}

 public void meanCollatzFilter(PImage srci) {
	srci.loadPixels();
	// ITERATE OVER IMAGE PIXELS
	for (int i = 0; i < srci.pixelWidth; i++){
		for (int j = 0; j < srci.pixelHeight; j++){
			// GET CURRENT PIXEL COLOR
			int index = (i + j * srci.pixelWidth);
			index = constrain(index, 0, srci.pixels.length -1);
			int cpx = srci.pixels[index];
			
			// GET EACH CHANNEL
			int rpx = cpx >> 16 & 0xFF;
			int gpx = cpx >> 8 & 0xFF;
			int bpx = cpx & 0xFF;
			
			// APPLY COLLATZ TO CHANNEL AVG
			int avgpx = (rpx + gpx + bpx)/3;
			int npx = collatz(avgpx);
			srci.pixels[index] = color(npx);
		}
	}
	srci.updatePixels();
}

 public int collatz(int num){
	return (num % 2 == 0) ? (num / 2) : (3 * num + 1);
}

 public PImage imagefit(PImage s, float ds){
	// https://stackoverflow.com/questions/1373035/how-do-i-scale-one-rectangle-to-the-maximum-size-possible-within-another-rectang
	float sw = (float)s.pixelWidth;
	float sh = (float)s.pixelHeight;
	float scale = min(pixelWidth/sw, pixelHeight/sh);
	
	int nw = Math.round(sw*scale/ds);
	int nh = Math.round(sh*scale/ds);
	s.resize(nw, nh);
	return s;
}

 public PImage imagefit(String spath){
	PImage s = loadImage(spath);
	return imagefit(s, 1.0f);
}

 public PImage imagefit(PImage pmg){
	return imagefit(pmg, 1.0f);
}

 public PImage randomImage(int w, int h){
		PImage rimg = createImage(w,h, ARGB);
		rimg.loadPixels();
		for (int i = 0; i < rimg.width; i++){
			for (int j = 0; j < rimg.height; j++){
				int c = color(random(1,255));
				int index = (i + j * rimg.width);
				rimg.pixels[index] = c;
			}
		}
		rimg.updatePixels();
		return rimg;
	}


  public void settings() { size(600, 600, P3D);
pixelDensity(displayDensity()); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "p4_CollatzFilter" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
