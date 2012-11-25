package com.epam.pf.trauma.backend.config;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Initializer implements ApplicationContextInitializer<AnnotationConfigWebApplicationContext> {

    private CloudEnvironment cloudEnvironment = new CloudEnvironment();

    private boolean isCloudFoundry() {
        return cloudEnvironment.isCloudFoundry();
    }

   
   
    @Override
    public void initialize(AnnotationConfigWebApplicationContext applicationContext) {


        String profile = "default";
      /* if(isCloudFoundry()) {
            profile = "cloud";
        }*/
        applicationContext.getEnvironment().setActiveProfiles(profile);

        
        applicationContext.refresh();
    }



}
