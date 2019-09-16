<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$router->get('/', function () use ($router) {
    return $router->app->version();
});

$router->group(['prefix' => 'auth'], function() use ($router) {
  $router->post('/register', 'Auth@register');
  $router->post('/login', 'Auth@login');
});

$router->group(['middleware' => 'auth'], function() use ($router) {

  $router->group(['prefix' => 'profil'], function() use ($router) {
    $router->get('/', 'Profil@profil');
    $router->put('/', 'Profil@update');
  });

});
