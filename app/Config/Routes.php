<?php

use CodeIgniter\Router\RouteCollection;

/**
 * @var RouteCollection $routes
 */
$routes->get('/', 'Auth::index');

$routes->get('logout', 'Auth::logout');

$routes->group('auth', function () use ($routes) {
   
   $routes->post('login', 'Auth::login');

   $routes->post('register', 'Auth::register');
});

$routes->set404Override(\App\Controllers\Auth::class . '::forbidden');
