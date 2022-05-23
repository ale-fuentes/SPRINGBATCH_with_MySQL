package com.alefuuuu.SPRINGBATCH__with__MySQL.configuration;

import com.alefuuuu.SPRINGBATCH__with__MySQL.model.Country;
import com.alefuuuu.SPRINGBATCH__with__MySQL.proccesing.CountryProcessing;
import com.alefuuuu.SPRINGBATCH__with__MySQL.repository.CountryRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class BatchConfiguration {

    private static final Log LOG = LogFactory.getLog(BatchConfiguration.class);

    @Autowired // for has access to environment file or config file
    private Environment environment;

    // native architecture sprint batch
    @Autowired // for to build to jobs
    private JobBuilderFactory jobBuilderFactory;
    @Autowired // for to build to steps
    private StepBuilderFactory stepBuilderFactory;

    // for access file inner folder resources
    @Value("classpath:countries.csv")
    private Resource resource;

    @Autowired
    private CountryRepository countryRepository;

    // JOB of job launcher
    @Bean("importCountryJob")
    public Job importCountryFromCSVFilesJob() {
        return jobBuilderFactory
                .get("importCountryFromCSVFilesJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }


    // STEP of job
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Country, Country>chunk(4)
                .reader(reder())
                .writer(write())
                .processor(new CountryProcessing())
                .build();
    }


    // STEPS
    // :: item reader
    @Bean
    public ItemReader<Country> reder() {

        FlatFileItemReader<Country> reader = new FlatFileItemReader<Country>();
        reader.setResource(resource);
        reader.setLinesToSkip(1);

        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {
                {
                    setNames(new String[]{"country", "latitude", "longitude", "name"});
                }
            });
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Country>() {
                {
                    setTargetType(Country.class);
                }
            });
        }});

        return reader;
    }

    // :: item processor

    // :: item write
    // this is a interface that Spring just has implemented.
    // We can associate to the differences sources how to DBA, files, brockes, etc.
    @Bean
    public ItemWriter<Country> write() {
        return new ItemWriter<Country>() {
            @Override
            public void write(List<? extends Country> list) throws Exception {
                countryRepository.saveAll(list);
            }
        };
    }


}
