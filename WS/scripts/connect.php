<?php
 
class Connect {
 
    //constructeur par default
    function __construct() {
 
    }
 
    //destructeur
    function __destruct() {
        // $this->close();
    }
 
    //connection à la base de donnée
    public function connect() {
        require_once './scripts/config.php';
        $con = mysql_connect(HOST, USER, PASSWORD);
        mysql_select_db(DATABASE);
	//mysql_query("SET NAMES UTF8");
 
        return $con;
    }
 
    public function close() {
        mysql_close();
    }
 
} 
?>
