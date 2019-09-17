<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Carbon\Carbon;

class Balita extends Model {

    protected $table = 'balita_master';

    protected $guarded = [];

    protected $hidden = ['dusun_id','account_id'];

    protected $appends = [
        'umur'
    ];

    public function dusun(){
        return $this->belongsTo('App\Models\Dusun', 'dusun_id');
    }

    public function account(){
        return $this->belongsTo('App\Models\Account', 'account_id');
    }

    public function balitaClassification(){
        return $this->hasMany('App\Models\BalitaClassificationModel', 'balita_id');
    }

    public function getUmurAttribute() {
        $tgl_lahir = new Carbon($this->tanggal_lahir);
        return $tgl_lahir->diffInMonths(Carbon::today());
    }

}