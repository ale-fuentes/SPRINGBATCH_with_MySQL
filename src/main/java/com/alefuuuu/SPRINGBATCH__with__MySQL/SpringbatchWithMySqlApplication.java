package com.alefuuuu.SPRINGBATCH__with__MySQL;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // activate process batch and set configuration for spring
public class SpringbatchWithMySqlApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("importCountryJob")
    private Job job;

    public SpringbatchWithMySqlApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbatchWithMySqlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //declaring parameters.
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        // launch the job
        jobLauncher.run(job, jobParameters);

    }
}
