<?php

namespace App\Filters\Auth;

use CodeIgniter\Filters\FilterInterface;
use CodeIgniter\HTTP\RequestInterface;
use CodeIgniter\HTTP\ResponseInterface;

class Auth implements FilterInterface
{
   public function before(RequestInterface $request, $arguments = null)
   {
      $session = session();
      if (
         $session->has('session_token')
         && $session->has('user_token')
      ) {
         return redirect()->to('/');
      }
   }

   public function after(RequestInterface $request, ResponseInterface $response, $arguments = null)
   {
   }
}
