DROP TABLE IF EXISTS "public"."v_sys_ownership";

DROP VIEW IF EXISTS "public"."v_sys_ownership";
-- ----------------------------
-- View for v_sys_ownership
-- ----------------------------
CREATE VIEW v_sys_ownership AS
SELECT po.ownership_id,
       po.organization_id,
       o.organization_name,
       o.parent_id           AS organization_parent_id,
       po.department_id,
       d.department_name,
       d.parent_id           AS department_parent_id,
       po.employee_id,
       e.employee_name,
       e.email,
       e.pki_email,
       e.mobile_phone_number AS phone_number,
       e.IDENTITY,
       po.ranking,
       po.is_reserved,
       po.status,
       po.reversion,
       po.create_time,
       po.update_time
FROM sys_ownership po,
     sys_organization o,
     sys_department d,
     sys_employee e
WHERE po.organization_id = o.organization_id
  AND po.department_id = d.department_id
  AND po.employee_id = e.employee_id