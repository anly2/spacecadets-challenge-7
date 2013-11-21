<?
   //Constants
   define("default_port", 27327, 1);
   define("setting_autoreg", true, 1);
   define("db_users", "users.db", 1);
   define("db_contacts", "contacts.db", 1);


   //login
   if (!isset($_REQUEST['user'], $_REQUEST['pass']))
   {
      echo "No username and password are provided!";
      exit(1);
   }

   $users = file_exists(db_users) ? file(db_users) : array();
   $found = false;

   foreach ($users as $id => $details)
   {
      $details = explode(",", $details);

      if ($details[0] != $_REQUEST['user'])
         continue;

      if ($details[1] != $_REQUEST['pass'])
         continue;


      //We got our man
      $details[2] = $_SERVER['REMOTE_ADDR']; //His current IP
      $details[3] = isset($_REQUEST['port']) ? $_REQUEST['port'] : default_port;

      $users[$id] = implode(",", $details);
      file_put_contents (db_users, implode("\n", $users));

      $found = true;
      break;
   }

   if (!$found)
   {
      if (setting_autoreg)
      {
         $username = preg_replace ("/[^a-zA-Z0-9_\-\.@]/", "", $_REQUEST['user']);
         $password = preg_replace ("/[^a-zA-Z0-9_ ]/", "", $_REQUEST['pass']);

         $ip = $_SERVER['REMOTE_ADDR']; //His current IP
         $port = default_port;

         if (isset($_REQUEST['port']))
            $port = preg_replace("/\D/g", "", $_REQUEST['port']);

         $delimiter = empty($users)? "" : "\n";
         $r = file_put_contents (db_users, $delimiter."$username,$password,$ip,$port");
         //var_dump($r);
      }
   }

   //query
   //getContacts
   //addContact
?>