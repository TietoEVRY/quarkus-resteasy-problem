package com.tietoevry.quarkus.resteasy.problem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import org.zalando.problem.Problem;

import javax.inject.Singleton;

@Singleton
public class JacksonProblemModuleRegistrar implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule("RFC7807 problem");
        module.addSerializer(Problem.class, new JacksonProblemSerializer());
        mapper.registerModule(module);
    }

}