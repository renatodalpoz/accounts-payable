CREATE TABLE public.USER_SYSTEM (
	id SERIAL NOT NULL,
	login TEXT NOT NULL,
	password TEXT NOT NULL,
	role TEXT NOT NULL,
	CONSTRAINT USER_SYSTEM_pkey PRIMARY KEY (id)
);
CREATE INDEX USER_SYSTEM_id_idx ON public.USER_SYSTEM USING btree (id);

-- usuario padrão, admin, senha: admin
INSERT INTO public.user_system
(id, login, "password", "role")
VALUES(1, 'admin', '$2a$10$dfK2pvp0P3.fddCfjYPyB.o5dS1WAwDaCBY8Q769V1pt/9ntGjwWy', '0');
