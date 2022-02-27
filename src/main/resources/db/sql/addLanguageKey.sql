create procedure add_language(vlanguage_code character varying, vkey_code character varying, vdefault_text character varying)
    language plpgsql
as
$$
DECLARE
    pKey_code varchar;
    pKey_codes varchar [] ;
    vLanguage_keys s_language_keys;
    firstCodeId int;
    parentId int;
BEGIN
    pKey_codes := regexp_split_to_array(vKey_code,'\.');
    FOR counter IN 1..array_length ( pKey_codes, 1 ) LOOP

            IF (counter =1 ) THEN

                SELECT lk.* INTO vLanguage_keys FROM s_language_keys lk WHERE lk.language_code= vLanguage_code AND lk.key_code = pKey_codes [ 1 ] AND lk.parent_code IS NULL;
                IF ( vLanguage_keys IS NULL ) THEN
                    SELECT nextval('s_language_keys_id_seq') INTO firstCodeId;
                    INSERT INTO s_language_keys (id,language_code,key_code) VALUES (firstCodeId,vLanguage_code,pKey_codes [ counter ]);
                ELSE
                    firstCodeId:=vLanguage_keys.id;
                END IF;
            ELSEIF (counter=array_length(pKey_codes,1)) THEN

                SELECT lk.* INTO vLanguage_keys FROM s_language_keys lk WHERE lk.key_code = pKey_codes [ counter ] AND lk.parent_code=firstCodeId;
                IF (vLanguage_keys IS NULL) THEN
                    INSERT INTO s_language_keys (id,parent_code,language_code,key_code,default_text) VALUES (nextval('s_language_keys_id_seq'),firstCodeId,vLanguage_code,pKey_codes [ counter ],vDefault_text);
                ELSE
                    UPDATE s_language_keys SET default_text=vDefault_text WHERE id=vLanguage_keys.id;
                END IF;
            ELSE

                SELECT lk.* INTO vLanguage_keys FROM s_language_keys lk WHERE lk.parent_code=firstCodeId AND lk.key_code = pKey_codes [ counter ];
                IF (vLanguage_keys IS NULL) THEN
                    SELECT nextval('s_language_keys_id_seq') INTO parentId;
                    INSERT INTO s_language_keys (id,parent_code,language_code,key_code) VALUES (parentId,firstCodeId,vLanguage_code,pKey_codes [ counter ]);
                    firstCodeId:=parentId;
                ELSE
                    firstCodeId:=vLanguage_keys.id;
                END IF;
            END IF;
        END LOOP;
END;
$$;

alter procedure add_language(varchar, varchar, varchar) owner to postgres;

