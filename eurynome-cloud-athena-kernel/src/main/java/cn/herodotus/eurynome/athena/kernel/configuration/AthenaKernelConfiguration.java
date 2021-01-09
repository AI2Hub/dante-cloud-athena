/*
 * Copyright (c) 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud-athena
 * Module Name: eurynome-cloud-athena-kernel
 * File Name: AthenaKernelConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/1/6 下午12:00
 * LastModified: 2021/1/6 下午12:00
 */

package cn.herodotus.eurynome.athena.kernel.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud-athena </p>
 * <p>File: AthenaKernelConfiguration </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/6 12:00
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.athena.kernel.configuration",
        "cn.herodotus.eurynome.athena.kernel.service",
        "cn.herodotus.eurynome.athena.kernel.controller"
})
public class AthenaKernelConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Components [Athena Kernel] Auto Configure.");
    }
}
