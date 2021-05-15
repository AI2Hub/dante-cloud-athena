/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud-athena
 * Module Name: eurynome-cloud-athena-application
 * File Name: upms-data-postgresql.sql
 * Author: gengwei.zheng
 * Date: 2021/05/15 08:18:15
 */

-- ----------------------------
-- Table data for dev_supplier
-- ----------------------------
INSERT INTO "dev_supplier"("supplier_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "parent_id", "supplier_code", "supplier_name", "supplier_type") VALUES ('067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e', '2020-05-05 17:13:00.067', 0, '2020-05-05 17:13:00.067', '', 't', 0, 1, NULL, 'HERODOTUS', '业务中台架构及开发团队', 'CORE');

-- ----------------------------
-- Table data for oauth_microservices
-- ----------------------------
INSERT INTO "oauth_microservices"("service_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "app_code", "app_name", "app_secret", "app_type", "supplier_id") VALUES ('004b87d8-0a18-4e62-a35c-f2f123713349', '2020-05-06 11:24:06.377', 0, '2020-05-06 11:24:06.377', '', 't', 0, 1, 'eurynome-cloud-upms-ability', '业务中台用户中心服务', '2bda7d3a-dba1-45a4-b08e-cbd731a0418e', 1, '067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e');
INSERT INTO "oauth_microservices"("service_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "app_code", "app_name", "app_secret", "app_type", "supplier_id") VALUES ('e2a746fc-cb1a-49c3-9436-67004117b039', '2020-06-19 16:57:59.565', 2, '2020-06-19 16:57:59.565', '', 't', 0, 1, 'eurynome-cloud-bpmn-ability', '业务中台流程中心服务', '25c9a32b-45a9-447a-a7e3-3a28d6a6834e', 1, '067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e');
INSERT INTO "oauth_microservices"("service_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "app_code", "app_name", "app_secret", "app_type", "supplier_id") VALUES ('f8e3f156-2cf4-449c-926d-d1377fe82c86', '2020-05-05 17:14:49.183', 0, '2020-05-05 17:14:49.183', '', 't', 0, 1, 'eurynome-cloud-uaa', '业务中台认证中心服务', '067e9d1c-12ed-4400-92ce-97281ddd71ff', 1, '067fc1c8-f3e1-4f41-9c7c-0bd4f885bf9e');

-- ----------------------------
-- Table data for oauth_applications
-- ----------------------------
INSERT INTO "oauth_applications"("app_key", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "app_icon", "app_name", "app_name_en", "app_secret", "app_type", "app_tech", "website", "app_code") VALUES ('010e659a-4005-4610-98f6-00b822f4758e', '2020-04-21 19:00:19.197', 0, '2020-05-05 18:02:48.367', '', 't', 0, 1, NULL, '业务中台管理端', 'Eurynome', '04165a07-cffd-45cf-a20a-1c2a69f65fb1', 0, 3, 'http://localhost:8080', '');

-- ----------------------------
-- Table data for oauth_scopes
-- ----------------------------
INSERT INTO "oauth_scopes"("scope_id", "create_time", "ranking", "update_time", "description", "is_reserved", "reversion", "status", "scope_code", "scope_name") VALUES ('c153737a-5234-11ea-ae28-14cf92c9b916', '2020-04-14 16:06:12', 1, '2020-04-17 07:52:46.481', '中台全部服务权限', 't', 0, 1, 'all', '全部权限');

-- ----------------------------
-- Table data for sys_user
-- ----------------------------
INSERT INTO "sys_user"("user_id", "create_time", "ranking", "update_time", "description", "is_reserved", "status", "employee_id", "nick_name", "password", "user_name", "reversion") VALUES ('1', '2019-07-23 07:19:50', 1, '2019-07-23 07:19:52', '平台管理员', 't', 1, '', 'Hades', '$2a$10$fi5ecIcM3hy9RQwE0x78oeyNecPFiUgi0PnhESeENjX3G4CBvYOLO', 'system', NULL);

-- ----------------------------
-- Table data for sys_role
-- ----------------------------
INSERT INTO "sys_role"("role_id", "create_time", "ranking", "update_time", "description", "is_reserved", "status", "role_code", "role_name", "reversion") VALUES ('1', '2019-07-23 07:22:27', 1, '2019-09-11 12:04:52', '中台管理员角色', 't', 1, 'ROLE_ADMINISTRATOR', '平台管理员角色', NULL);

-- ----------------------------
-- Table data for sys_user_role
-- ----------------------------
INSERT INTO "sys_user_role"("user_id", "role_id") VALUES ('1', '1');



