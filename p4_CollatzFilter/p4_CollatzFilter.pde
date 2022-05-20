// FILE: CollatzFilter
// AUTHOR: Faizon Zaman
PImage source, img;
float dnsmp, dscale;
void setup(){
	// CANVAS SETTINGS
	size(600,600,P3D);
	pixelDensity(displayDensity());
	
	// IMAGE SETTINGS
	dnsmp = 1.00; /* downsample factor => 1.00: least, +: most */
	
	/* imagefit fits the image to screen preserving aspect ratio */
	/* imagefit takes the path or the PImage as its first argument */
	/* imagefit optionally takes a downsample factor as its second argument */
	
	// source = imagefit("./imgs/clouds.jpg", dnsmp);
	source = imagefit(randomImage(width/100,height/100), dnsmp);
	imageMode(CENTER); /* set the image's anchor to its center */
	dscale = (dnsmp/2.); /* set the display scale of the image */
}

void draw(){
	collatzFilter(source); /* apply collatz to r,g,b channels */
	// meanCollatzFilter(source); /* apply collatz to rgb avg */
	image(source, width/2, height/2, source.pixelWidth*dscale, source.pixelHeight*dscale);
}

void collatzFilter(PImage srci) {
	srci.loadPixels();
	for (int i = 0; i < srci.pixelWidth; i++){
		for (int j = 0; j < srci.pixelHeight; j++){
			// GET CURRENT PIXEL COLOR
			int index = (i + j * srci.pixelWidth);
			index = constrain(index, 0, srci.pixels.length -1);
			color cpx = srci.pixels[index];
			
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

void meanCollatzFilter(PImage srci) {
	srci.loadPixels();
	// ITERATE OVER IMAGE PIXELS
	for (int i = 0; i < srci.pixelWidth; i++){
		for (int j = 0; j < srci.pixelHeight; j++){
			// GET CURRENT PIXEL COLOR
			int index = (i + j * srci.pixelWidth);
			index = constrain(index, 0, srci.pixels.length -1);
			color cpx = srci.pixels[index];
			
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

int collatz(int num){
	return (num % 2 == 0) ? (num / 2) : (3 * num + 1);
}

PImage imagefit(PImage s, float ds){
	// https://stackoverflow.com/questions/1373035/how-do-i-scale-one-rectangle-to-the-maximum-size-possible-within-another-rectang
	float sw = (float)s.pixelWidth;
	float sh = (float)s.pixelHeight;
	float scale = min(pixelWidth/sw, pixelHeight/sh);
	
	int nw = Math.round(sw*scale/ds);
	int nh = Math.round(sh*scale/ds);
	s.resize(nw, nh);
	return s;
}

PImage imagefit(String spath){
	PImage s = loadImage(spath);
	return imagefit(s, 1.0);
}

PImage imagefit(PImage pmg){
	return imagefit(pmg, 1.0);
}

PImage randomImage(int w, int h){
		PImage rimg = createImage(w,h, ARGB);
		rimg.loadPixels();
		for (int i = 0; i < rimg.width; i++){
			for (int j = 0; j < rimg.height; j++){
				color c = color(random(1,255));
				int index = (i + j * rimg.width);
				rimg.pixels[index] = c;
			}
		}
		rimg.updatePixels();
		return rimg;
	}
