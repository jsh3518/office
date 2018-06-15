package com.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.Invoice;
import com.office.mapper.InvoiceMapper;
import com.office.service.InvoiceService;


@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private InvoiceMapper invoiceMapper;
	
	public InvoiceMapper getInvoiceMapper() {
		return invoiceMapper;
	}

	public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
		this.invoiceMapper = invoiceMapper;
	}

	public List<Invoice> listAllInvoice(Invoice invoice) {
		return invoiceMapper.listAllInvoice(invoice);
	}
	
	public List<Invoice> listPageInvoice(Invoice invoice) {
		return invoiceMapper.listPageInvoice(invoice);
	}
	
	public Invoice getInvoice(Invoice invoice) {	
		return invoiceMapper.getInvoice(invoice);
	}
	
	@Override
	public Invoice getInvoiceById (Integer id) {
		
		return invoiceMapper.getInvoiceById(id);
	}

	@Override
	public boolean insert(Invoice invoice) {
		
		invoiceMapper.insertInvoice(invoice);
		return true;
	}

	@Override
	public void updateInfo(Invoice invoice) {
		
		invoiceMapper.updateInfo(invoice);
	}

	@Override
	public void delete(Integer id) {

		invoiceMapper.delete(id);
	}

	
	
}
