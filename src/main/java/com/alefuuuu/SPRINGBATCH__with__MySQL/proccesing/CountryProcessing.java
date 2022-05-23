package com.alefuuuu.SPRINGBATCH__with__MySQL.proccesing;

import com.alefuuuu.SPRINGBATCH__with__MySQL.model.Country;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

public class CountryProcessing implements ItemProcessor<Country, Country> {

    private static final Log LOG = LogFactory.getLog(CountryProcessing.class);

    @Override // here has the logic of business.
    public Country process(Country country) throws Exception {
        LOG.info("PROCESSING COUNTRY >> " + country.toString());
        return country;
    }
}
