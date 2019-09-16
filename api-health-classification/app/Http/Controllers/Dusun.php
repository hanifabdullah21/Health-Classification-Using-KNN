<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Dusun as DusunModel;

class Dusun extends Controller{

    public function __construct(){
        parent::__construct();        
    }

    public function getDusun(){
        return $this->response->success(DusunModel::get());
    }

    public function addDusun(Request $req){
        $dusun = DusunModel::create($req->only('nama'));
        return $this->response->success($dusun);
    }

}