package com.mbank.bank;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;

public class FutureLine implements ILineDrawer {

	private float lineWidth = 1;
	private float offset = 5;
	private Color color = Color.BLACK;

	@Override
	public void draw(PdfCanvas canvas, Rectangle drawArea) {
		canvas.saveState()
				.setStrokeColor(color)
				.setLineWidth(lineWidth)
				.moveTo(drawArea.getX(), drawArea.getY() + lineWidth / 2 + offset)
				.lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + lineWidth / 2 + offset)
				.stroke()
				.restoreState();
	}

	@Override
	public float getLineWidth() {
		return lineWidth;
	}

	@Override
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(float poffset) {
		this.offset = offset;
	}
}
