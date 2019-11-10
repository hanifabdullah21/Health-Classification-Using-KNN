<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Balita as BalitaModel;
use App\Models\BalitaClassificationModel;
use App\Models\BalitaTrainingModel;
use App\Models\BalitaTestModel;
use Validator;

class Balita extends Controller{

    public function __construct(){
        parent::__construct();
    }

    public function getListBalita(Request $req){
        return $this->response->success(BalitaModel::with('dusun','account')->get());
    }

    public function getListBalitaFilter(Request $req){
        $balita = BalitaModel::when($req->filled('nama'), function($q) use ($req) {
            $q->where('nama','like','%'.$req->nama.'%');
        })->when($req->filled('dusun_id'), function($q) use ($req) {
            $q->where('dusun_id', $req->dusun_id);
        });
        return $this->response->success($balita->with('dusun','account')->get());
    }

    public function addBalita(Request $req){
        $validator = Validator::make($req->all(),[
            'nama' => 'required|string',
            'dusun_id' => 'required|integer',
            'jenis_kelamin' => 'required|in:L,P',
            'tanggal_lahir' => 'required|date'
        ]);

        if($validator->fails()) return $this->response->notValidInput($validator->errors());

        $req->merge(['account_id'=>$req->account->id]);
        $balita = BalitaModel::create($req->only('account_id','dusun_id','nama','jenis_kelamin','tanggal_lahir'));
        $balita->load('dusun','account');
        return $this->response->success($balita);
    }

    public function updateBalita(Request $req){
        $validator = Validator::make($req->all(),[
            'nama' => 'required|string',
            'dusun_id' => 'required|integer',
            'jenis_kelamin' => 'required|in:L,P',
            'tanggal_lahir' => 'required|date',
            'balita_id' => 'required|integer'
        ]);

        if($validator->fails()) return $this->response->notValidInput($validator->errors());

        $balita = BalitaModel::with('dusun','account')->find($req->balita_id);
        $balita->update($req->only('nama', 'dusun_id', 'jenis_kelamin', 'tanggal_lahir'));
        return $this->response->success($balita);
    }

    public function deleteBalita(Request $req){
        $balita = BalitaModel::find($req->balita_id);
        $balita->delete();
        return $this->response->success(null);
    }

    public function addBalitaClassification(Request $req){
        $validator = Validator::make($req->all(),[

        ]);

        if($validator->fails()) return $this->response->notValidInput($validator->errors());

        $tanggalSamaPosyandu = BalitaClassificationModel::where('balita_id', $req->balita_id)
          ->where('tanggal_posyandu', $req->tanggal_posyandu)->first();

        if ($tanggalSamaPosyandu) return $this->response->error('sudah melakukan pemeriksaan pada tanggal '.$req->tanggal_posyandu);

        $req->merge(['account_id'=>$req->account->id]);
        $balitaClassification = BalitaClassificationModel::create($req->only('account_id','balita_id','umur','tanggal_posyandu','berat_badan','tinggi_badan','status'));
        return $this->response->success($balitaClassification->with('account','balita')->orderBy('id','desc')->first());
    }

    public function getListBalitaClassification(Request $req){
        $balita = BalitaClassificationModel::with('balita.dusun')
        ->when($req->filled('balita_id'), function($q) use ($req){
          $q->where('balita_id', $req->balita_id);
        })
        ->when($req->filled('nama'), function($q) use ($req) {
          $q->whereHas('balita', function($b) use ($req){
            $b->where('nama', 'LIKE', "%$req->nama%");
          });
        })
        ->when($req->filled('jenis_kelamin'), function($q) use ($req) {
          $q->whereHas('balita', function($b) use ($req){
            $b->where('jenis_kelamin', $req->jenis_kelamin);
          });
        })
        ->when($req->filled('dusun_id'), function($q) use ($req) {
          $q->whereHas('balita', function($b) use ($req){
            $b->where('dusun_id', $req->dusun_id);
          });
        })
        ->when($req->filled('range_tanggal'), function($q) use ($req) {
          $range = explode('to', $req->range_tanggal);
          $q->whereBetween('tanggal_posyandu', $range);
        })
        ->when($req->filled('status'), function($q) use ($req) {
          $q->where('status', $req->status);
        });
        return $this->response->success($balita->get());
    }


    public function addBalitaTraining(Request $req){
        $balitaTraining = BalitaTrainingModel::create($req->only('umur', 'berat_badan', 'tinggi_badan', 'jenis_kelamin','status'));
        return $this->response->success($balitaTraining->orderBy('id','desc')->first());
    }

    public function getBalitaTraining(){
        $balita = BalitaTrainingModel::with()
        ->when($req->filled('balita_id'), function($q) use ($req){
          $q->where('balita_id', $req->balita_id);
        });
        return $this->response->success($balita->get());
    }

    public function addBalitaTest(Request $req){
      $balita = BalitaTestModel::create($req->only('umur', 'berat_badan', 'tinggi_badan', 'jenis_kelamin','status'));
      return $this->response->success($balita->orderBy('id','desc')->first());
  }

  public function getBalitaTest(){
      return $this->response->success(BalitaTestModel::get());
  }
}
