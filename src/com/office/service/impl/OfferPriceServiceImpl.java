package com.office.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.OfferPrice;
import com.office.mapper.OfferPriceMapper;
import com.office.service.OfferPriceService;

@Service("OfferPriceService")
public class OfferPriceServiceImpl implements OfferPriceService {

	@Resource(name="offerPriceMapper")
	@Autowired
	private OfferPriceMapper offerPriceMapper;
	
	public OfferPriceMapper getOfferPriceMapper() {
		return offerPriceMapper;
	}

	public void setOfferPriceMapper(OfferPriceMapper offerPriceMapper) {
		this.offerPriceMapper = offerPriceMapper;
	}

	/*
	 * 查询产品价格列表
	 * @see com.office.service.OfferPriceService#listPageOfferPrice(com.office.entity.OfferPrice)
	 */
	public List<OfferPrice> listPageOfferPrice(OfferPrice offerPrice) {
		return offerPriceMapper.listPageOfferPrice(offerPrice);
	}
	
	/*
	 * 根据id查询产品价格
	 * @see com.office.service.OfferPriceService#getOfferPriceById(java.lang.String)
	 */
	public OfferPrice getOfferPriceById(String id){
		return offerPriceMapper.getOfferPriceById(id);
	}
	
	/*
	 *  保存产品价格
	 * @see com.office.service.OfferPriceService#saveOfferPrice(com.office.entity.OfferPrice)
	 */
	public void saveOfferPrice(OfferPrice offerPrice) {
		Integer id = offerPrice.getId();
		if(id==null||"".equals(id)){
			offerPriceMapper.insertOfferPrice(offerPrice);
		}else{
			offerPriceMapper.updateOfferPrice(offerPrice);
		}
	}

}
