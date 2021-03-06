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

  $router->group(['prefix' => 'dusun'], function() use ($router){
      $router->get('/', 'Dusun@getDusun');
      $router->post('/', 'Dusun@addDusun');
  });

  $router->group(['prefix' => 'profil'], function() use ($router) {
    $router->get('/', 'Profil@profil');
    $router->put('/', 'Profil@update');
  });

  $router->group(['prefix' => 'balita'], function() use ($router){
    $router->post('/', 'Balita@addBalita');
    $router->get('/', 'Balita@getListBalita');
    $router->get('/filter', 'Balita@getListBalitaFilter');
    $router->post('/delete', 'Balita@deleteBalita');
    $router->post('/update', 'Balita@updateBalita');
    $router->post('/classification', 'Balita@addBalitaClassification');
    $router->get('/classification', 'Balita@getListBalitaClassification');
    $router->post('/training', 'Balita@addBalitaTraining');
    $router->get('/training', 'Balita@getBalitaTraining');
    $router->post('/test', 'Balita@addBalitaTest');
    $router->get('/test', 'Balita@getBalitaTest');
  });

  $router->group(['prefix' => 'bumil'], function() use ($router){
    $router->post('/', 'Bumil@addBumil');
    $router->get('/', 'Bumil@getListBumil');
    $router->get('/filter', 'Bumil@getListBumilFilter');
    $router->post('/delete', 'Bumil@deleteBumil');
    $router->post('/update', 'Bumil@updateBumil');
    $router->post('/classification','Bumil@addBumilClassification');
    $router->get('/classification','Bumil@getListBumilClassification');
    $router->post('/training','Bumil@addBumilTraining');
    $router->get('/training','Bumil@getBumilTraining');
    $router->post('/test','Bumil@addBumilTest');
    $router->get('/test','Bumil@getBumilTest');
  });
});
