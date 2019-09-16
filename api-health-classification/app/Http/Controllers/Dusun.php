<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Dusun;

class Dusun extends Controller{

    public function __construct(){
        parent::__construct();        
    }

    public function getDusun(){
        return $this->response->success(Dusun::get());
    }

}