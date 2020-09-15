package P1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * File Name: Main2D.java
 * Date: January 24, 2020
 * Author: Alexander Szelestey
 * Purpose:  Use Java 2D graphic methods to rotate, scale, and translate each of the images.
 */

public class Main2D extends JPanel {

	// Default serial ID
	private static final long serialVersionUID = 1L;

	// Variables
	private int frameNumber;
	private long elapsedTimeMillis;
	private float pixelSize;

	static int translateX = 0;
	static int translateY = 0;
	static double rotation = 0.0;
	static double scaleX = 1.0;
	static double scaleY = 1.0;

	// Image Constructor
	Images myImages = new Images();
	BufferedImage letteri = myImages.getImage(Images.letteri);
	BufferedImage exclamation = myImages.getImage(Images.exclamation);
	BufferedImage trapezoid = myImages.getImage(Images.trapezoid);

	// Generated Getters and Setters
	public long getElapsedTimeMillis() {
		return elapsedTimeMillis;
	}

	public void setElapsedTimeMillis(long elapsedTimeMillis) {
		this.elapsedTimeMillis = elapsedTimeMillis;
	}

	public float getPixelSize() {
		return pixelSize;
	}

	public void setPixelSize(float pixelSize) {
		this.pixelSize = pixelSize;
	}

	// GUI Constructor
	public static void main(String[] args) {

		// Window setup
		JFrame window = new JFrame("Project 1: 2D Graphics");
		Main2D panel = new Main2D();
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);

		// Center GUI on Screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((screen.width - window.getWidth()) / 2, (screen.height - window.getHeight()) / 2);

		// Modified original code form AnimationStarter.java
		Timer animationTimer = new Timer(1900, new ActionListener() {

			// Sets time and number of frames to recycle through
			public void actionPerformed(ActionEvent arg0) {
				if (panel.frameNumber > 7) {
					panel.frameNumber = 0;
				} else {
					panel.frameNumber++;
				}
				final long startTime = System.currentTimeMillis();
				panel.setElapsedTimeMillis(System.currentTimeMillis() - startTime);
				panel.repaint();
			}
		});
		animationTimer.start(); // Start the animation running.
	}

	public Main2D() {
		// Size of Frame
		setPreferredSize(new Dimension(500, 500));
	}

	// // Modified original code form AnimationStarter.java
	protected void paintComponent(Graphics g) {

		// Sets up new panel
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());

		// Controls your zoom and area you are looking at
		applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);

		// Switch statement instructs each transformation
		System.out.println("On Frame " + frameNumber);
		switch (frameNumber) {
		case 1:
			translateX = 0;
			translateY = 0;
			scaleX = 1.0;
			scaleY = 1.0;
			rotation = 0;
			break;
		case 2:
			translateX += -5;
			break;
		case 3:
			translateY += 7;
			break;
		case 4:
			rotation += 45 * Math.PI / 180.0;
			break;
		case 5:
			rotation += -90 * Math.PI / 180.0;
			break;
		case 6:
			scaleX = 2.0;
			break;
		case 7:
			scaleY = 0.5;
			break;
		}

		AffineTransform savedTransform = g2.getTransform();

		// Defines placement of the letter i on startup.
		g2.translate(translateX, translateY);
		g2.translate(-12, 40);
		g2.rotate(-1.55);
		g2.rotate(rotation);
		g2.scale(scaleX, scaleY);
		g2.drawImage(letteri, 0, 0, this);
		g2.setTransform(savedTransform);

		// Defines placement of the exclamation mark on start up.
		g2.translate(translateX, translateY);
		g2.translate(12, 40);
		g2.rotate(-1.55);
		g2.rotate(rotation);
		g2.scale(scaleX, scaleY);
		g2.drawImage(exclamation, 0, 0, this);
		g2.setTransform(savedTransform);

		// Defines placement of the trapezoid on start up.
		g2.translate(translateX, translateY);
		g2.translate(0, 0);
		g2.rotate(-1.55);
		g2.rotate(rotation);
		g2.scale(scaleX, scaleY);
		g2.drawImage(trapezoid, 0, 0, this);
		g2.setTransform(savedTransform);
	}

	// Method taken directly from AnimationStarter.java Code
	private void applyWindowToViewportTransformation(Graphics2D g2, double left, double right, double bottom,
			double top, boolean preserveAspect) {
		int width = getWidth();
		int height = getHeight();
		if (preserveAspect) {
			double displayAspect = Math.abs((double) height / width);
			double requestedAspect = Math.abs((bottom - top) / (right - left));
			if (displayAspect > requestedAspect) {
				double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
				bottom += excess / 2;
				top -= excess / 2;
			} else if (displayAspect < requestedAspect) {
				double excess = (right - left) * (requestedAspect / displayAspect - 1);
				right += excess / 2;
				left -= excess / 2;
			}
		}
		g2.scale(width / (right - left), height / (bottom - top));
		g2.translate(-left, -top);
		double pixelWidth = Math.abs((right - left) / width);
		double pixelHeight = Math.abs((bottom - top) / height);
		setPixelSize((float) Math.max(pixelWidth, pixelHeight));
	}
}
