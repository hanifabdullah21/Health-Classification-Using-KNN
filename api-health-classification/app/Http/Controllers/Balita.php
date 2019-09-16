<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Balita as BalitaModel;
use Validator;

class Balita extends Controller{

    public function __construct(){
        parent::__construct();
    }

    public function getListBalita(){
        return $this->response->success(BalitaModel::with('dusun','account')->get());
    }

    public function addBalita(Request $req){
        $validator = Validator::make($req->all(),[
            'nama' => 'required|string'
        ]);

        if($validator->fails()) return $this->response->notValidInput($validator->errors());

        $req->merge(['account_id'=>$req->account->id]);
        $balita = BalitaModel::create($req->only('account_id','dusun_id','nama','jenis_kelamin','tanggal_lahir'));
        return $this->response->success($balita->with('dusun','account')->first());
    }

    public function updateBalita(){}

    public function addBalitaClassification(){}

    public function getListBalitaClassification(){}

}