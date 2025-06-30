CREATE TABLE text_stats (
                            id SERIAL PRIMARY KEY,
                            job_id BIGINT,
                            word_count INTEGER,
                            line_count INTEGER,
                            file_names TEXT[],
                            words_freq TEXT
);