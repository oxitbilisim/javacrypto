INSERT INTO public.s_user (id, email, password, first_name, last_name, is_enabled) VALUES (nextval('seq_s_user'), 'admin@admin.com', '$2a$10$itUkieoR42Eiwpx7//0loePFz6xJa8x20N1tGN9JqhQyNJGFMF.Ki', 'Şeref', 'SEVEN', true);
INSERT INTO public.s_role (id, role_name) VALUES (nextval('seq_s_role'), 'ROLE_ADMIN');
INSERT INTO public.s_user_role (user_id, role_id) VALUES (currval('seq_s_user'), currval('seq_s_role'));

