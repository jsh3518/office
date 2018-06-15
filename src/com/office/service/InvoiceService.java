package com.office.service;

import java.util.List;
import com.office.entity.Invoice;

public interface InvoiceService {
	
	List<Invoice> listAllInvoice(Invoice invoice);
	
	List<Invoice> listPageInvoice(Invoice invoice);
	
	Invoice getInvoice(Invoice Invoice);
	
	Invoice getInvoiceById(Integer id);
	
	void delete(Integer id);
	
	boolean insert(Invoice invoice);
	
	void updateInfo(Invoice invoice);
	
}
