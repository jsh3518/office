package com.office.mapper;

import java.util.List;

import com.office.entity.Invoice;


public interface InvoiceMapper {

	//查询全部发票信息
	List<Invoice> listAllInvoice(Invoice invoice);
	//查询全部发票信息
	List<Invoice> listPageInvoice(Invoice invoice);
	//查询发票信息
	Invoice getInvoice(Invoice invoice);
	//根据id查找发票信息
	Invoice getInvoiceById(Integer id);
	//删除发票
	void delete(Integer id);
	//新增发票
	void insertInvoice(Invoice invoice);
	//修改发票
	void updateInfo(Invoice invoice);
	
}
