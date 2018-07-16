# Generátor SQL dotazů
Program slouží k vytváření komplikovanějších SQL dotazů, ve kterých by se při ručním psaní mohly objevit překlepy. Na začátku uživatel zadá soubor s SQLite databází, ze které program zjistí jména jak samotných tabulek, tak jejich sloupců. Poté už lze  zadávat pomocí windows-like rozhraní parametry dotazu.
Pro otestování funcionality je k programu přiložen databázový soubor vzorky.db.
Na připojení do SQLite databáze program používá knihovnu sqlite-jdbc-3.21.0.jar, která je přiložena též. Byla stažena z https://bitbucket.org/xerial/sqlite-jdbc/downloads/...
