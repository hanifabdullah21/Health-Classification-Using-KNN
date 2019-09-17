<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Balita as BalitaModel;
use App\Models\BalitaClassificationModel;
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
        return $this->response->success($balita->with('dusun','account')->orderBy('id','desc')->first());
    }

    public function updateBalita(){}

    public function addBalitaClassification(Request $req){
        $validator = Validator::make($req->all(),[
            
        ]);

        if($validator->fails()) return $this->response->notValidInput($validator->errors());

        $req->merge(['account_id'=>$req->account->id]);
        $balitaClassification = BalitaClassificationModel::create($req->only('account_id','balita_id','umur','tanggal_posyandu','berat_badan','tinggi_badan','status'));
        return $this->response->success($balitaClassification->with('account','balita')->first());
    }

    public function getListBalitaClassification(Request $req){
        $balita = BalitaClassificationModel::where('balita_id', $req->balita_id);
        return $this->response->success($balita->get());
    }

}