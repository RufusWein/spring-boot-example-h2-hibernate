package com.example.springboot.prices.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.prices.controller.response.ItemResult;
import com.example.springboot.prices.dao.PriceRepository;
import com.example.springboot.prices.model.Price;

@Service
public class PricesService {

    @Autowired
    private PriceRepository pricesRepo;

	public List<ItemResult> doQuery(String appDate, Integer productId, Integer brandId) throws Exception {
		boolean isValidAppDate = appDate != null && validAppDate(appDate);
		List<ItemResult> queryList = new ArrayList<ItemResult>();
		List<Price> prices = pricesRepo.findAll();
		prices.forEach(price ->{
			boolean valid = true;
			if (isValidAppDate)
				try {
					valid = isAppDateBetweenPriceDate(appDate, price);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			if (productId != null && valid) valid = (productId == price.getProductId());
			if (brandId != null && valid) valid = (brandId == price.getBrandId());
				
			if (valid) {
				ItemResult newQuery = new ItemResult();
				newQuery.setProductId(price.getProductId());
				newQuery.setBrandId(price.getBrandId());
				newQuery.setPriceList(price.getPriceList());
				newQuery.setStartDate(price.getStartDate());
				newQuery.setEndDate(price.getEndDate());
				newQuery.setPrice(price.getPrice());
				queryList.add(newQuery);
			}
		});
		
		return queryList;
	}

	private boolean isAppDateBetweenPriceDate(String appDateStr, Price price) throws ParseException {
		Date appDate = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").parse(appDateStr);
		Date startDatePrice = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(price.getStartDate());
		Date endDatePrice = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(price.getEndDate());
		
		return (appDate.equals(endDatePrice) || appDate.equals(startDatePrice) ||
				(appDate.after(startDatePrice) && appDate.before(endDatePrice)));
	}

	private boolean validAppDate(String appDate) throws Exception {
        try {
            new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").parse(appDate);
        } catch (DateTimeParseException | ParseException e) {
        	throw new Exception("Error: Date format is wrong!");
        }
		return true;
	}
    
}
