package com.mbank.bank;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.property.TabAlignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionPdf {

	public static void createPdf(File dest, TransactionEntity transaction) throws IOException {
		PdfDocument pdf = new PdfDocument(new PdfWriter(dest.getAbsolutePath()));
		PageSize pagesize = PageSize.A4;
		Document document = new Document(pdf, pagesize);
		float w = pagesize.getWidth() - document.getLeftMargin() - document.getRightMargin();
		FutureLine line = new FutureLine();
		List<TabStop> tabstops = new ArrayList();
		tabstops.add(new TabStop(w / 2, TabAlignment.CENTER, line));
		tabstops.add(new TabStop(w, TabAlignment.LEFT, line));
		Paragraph p = new Paragraph();
		p.addTabStops(tabstops);
		p.add(new Tab()).add(" Custom my text ").add(new Tab());
		p.add(new Tab()).add(TransactionMessage.transactionHeader(transaction));
		document.add(p);
		document.close();
	}
}
