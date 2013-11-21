<?
   //Constants
   define("default_port", 27327, 1);
   define("setting_autoreg", true, 1);
   define("db_users", "users.db", 1);
   define("db_contacts", "contacts.db", 1);


   //login
if (isset($_REQUEST['login']))
{
   if (!isset($_REQUEST['user'], $_REQUEST['pass']))
   {
      echo "No username and password were provided!";
      exit(1);
   }

   $users = array (
      "anko,okna,,",
      "halit,tilah,,"
   );

   //$users = file_exists(db_users) ? file(db_users) : array();
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
      echo "Sueccessfully logged in {".$details[0]."}";
      echo "\n".$details[2];
      break;
   }

   if (!$found)
   {
      if (!setting_autoreg)
      {
         echo "Username and/or password were invalid!";
         exit (1);
      }


      //Automatic Registration

      $username = preg_replace ("/[^a-zA-Z0-9_\-\.@]/", "", $_REQUEST['user']);
      $password = preg_replace ("/[^a-zA-Z0-9_ ]/", "", $_REQUEST['pass']);

      $ip = $_SERVER['REMOTE_ADDR']; //His current IP
      $port = default_port;

      if (isset($_REQUEST['port']))
         $port = preg_replace("/\D/g", "", $_REQUEST['port']);

      array_push ($users, $username,$password,$ip,$port);
      file_put_contents (db_users, implode("\n", $users));
      file_put_contents (db_contacts, "\n", FILE_APPEND);

      echo "Successfully registered {$username}";
   }

   exit (0);
}

   //query
if (isset($_REQUEST['query']))
{
   if (!isset($_REQUEST['user']))
   {
      echo "No username provided!";
      exit(1);
   }

   $users = array (
      "anko,okna,152.78.236.39,27327",
      "halit,tilah,,27327"
   );

   //$users = file_exists(db_users) ? file(db_users) : array();

   foreach ($users as $id => $details)
   {
      $details = explode (",", $details);

      if ($details[0] != $_REQUEST['user'])
         continue;

      if (trim($details[2]) == "" || trim($details[3]) == "")
      {
         echo "Failure: User not logged in.";
         exit (1);
      }

      echo $details[2].":".$details[3] .":Success";
      exit (0);
   }

   echo "Failure: User not found";
   exit (1);
}

   //getContacts
if (isset($_REQUEST['contacts']))
{
   if (!isset($_REQUEST['user']))
   {
      echo "Failure: No username provided!";
      exit(1);
   }



   $users = array (
      "anko,okna,,",
      "halit,tilah,,"
   );

   $contacts = array (
      "halit,ivo,dancho,jijo",
      "anko,ivo,dancho,jijo"
   );

   //$users = file_exists(db_users) ? file(db_users) : array();
   //$contacts = file_exists(db_contacts) ? file(db_contacts) : array();

   foreach ($users as $id => $details)
   {
      $details = explode(",", $details);

      if ($details[0] != $_REQUEST['user'])
         continue;

      echo $contacts[$id];
      exit (0);
   }

   echo "Failure: User not found";
   exit (1);
}

   //addContact
if (isset($_REQUEST['add'], $_REQUEST['contact']))
{
   if (!isset($_REQUEST['user'], $_REQUEST['contact']))
   {
      echo "Failure: No username and contact name provided!";
      exit(1);
   }



   $users = array (
      "anko,okna,,",
      "halit,tilah,,"
   );

   $contacts = array (
      "halit,ivo,dancho,jijo",
      "anko,ivo,dancho,jijo"
   );

   //$users = file_exists(db_users) ? file(db_users) : array();
   //$contacts = file_exists(db_contacts) ? file(db_contacts) : array();

   foreach ($users as $id => $details)
   {
      $details = explode(",", $details);

      if ($details[0] != $_REQUEST['user'])
         continue;

      $contact = preg_replace ("/[^a-zA-Z0-9_\-\.@]/", "", $_REQUEST['user']);
      $delimiter = (trim($contacts[$id]) == "") ? "" : ",";

      $contacts[$id] .= $delimiter.$contact;

      echo "Successfully added {$contact} to {".$details[0]."}'s contacts.";
      exit (0);
   }

   echo "Failure: User not found";
   exit (1);
}
?>