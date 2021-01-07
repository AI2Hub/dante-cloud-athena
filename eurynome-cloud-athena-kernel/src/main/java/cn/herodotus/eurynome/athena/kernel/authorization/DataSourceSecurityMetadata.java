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
 * File Name: DataSourceSecurityMetadata.java
 * Author: gengwei.zheng
 * Date: 2021/1/6 上午11:49
 * LastModified: 2020/12/31 下午6:34
 */

package cn.herodotus.eurynome.athena.kernel.authorization;

import cn.herodotus.eurynome.security.definition.RequestMapping;
import cn.herodotus.eurynome.security.strategy.SecurityMetadataStorage;

import java.util.List;

/**
 * <p>Project: eurynome-cloud-athena </p>
 * <p>File: DataSourceSecurityMetadata </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/12/30 14:54
 */
public class DataSourceSecurityMetadata extends SecurityMetadataStorage {
    @Override
    public void save(List<RequestMapping> requestMappings) {

    }

    @Override
    public List<RequestMapping> findAll() {
        return null;
    }
}
