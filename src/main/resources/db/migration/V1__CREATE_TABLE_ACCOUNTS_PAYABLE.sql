CREATE TABLE public.ACCOUNTS_PAYABLE (
	id SERIAL NOT NULL,
	data_vencimento date NOT NULL,
	data_pagamento date NOT NULL,
	valor numeric(21, 2) NOT NULL,
	descricao varchar(255) NULL,
	situacao bool NOT NULL DEFAULT false,
	CONSTRAINT ACCOUNTS_PAYABLE_pkey PRIMARY KEY (id)
);
CREATE INDEX ACCOUNTS_PAYABLE_id_idx ON public.ACCOUNTS_PAYABLE USING btree (id);
