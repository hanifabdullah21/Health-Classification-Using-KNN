<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\BumilClassificationModel;
use Validator;

class Bumil extends Controller{

    public function __construct(){
        parent::__construct();
    }

    public function addBumilClassification(Request $req){
        $validator = Validator::make($req->all(),[

        ]);
    
        if($validator->fails()) return $this->response->notValidInput($validator->errors());

        $req->merge(['account_id'=>$req->account->id]);
        $bumilClassification = BumilClassificationModel::create($req->only('account_id','dusun_id','nama','usia_bumil','usia_kehamilan','berat_badan','tinggi_badan','LILA','KEK','status'));
        return $this->response->success($bumilClassification->with('account')->orderBy('id','desc')->first());
    }

    public function getListBumilClassification(Request $req){
        $bumil = BumilClassificationModel::with('dusun')
        ->when($req->filled('id'), function($q) use ($req){
          $q->where('id', $req->id);
        })
        ->when($req->filled('nama'), function($q) use ($req) {
            $q->where('nama', 'LIKE', "%$req->nama%");
        })
        ->when($req->filled('dusun_id'), function($q) use ($req) {
            $q->where('dusun_id', $req->dusun_id);
        })
        ->when($req->filled('usia_kehamilan'), function($q) use ($req) {
            $q->where('usia_kehamilan', $req->usia_kehamilan);
        })
        ->when($req->filled('status'), function($q) use ($req) {
            $q->where('status', $req->status);
        });
        return $this->response->success($bumil->get());
    }
}