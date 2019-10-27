<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class BalitaClassificationModel extends Model{

    protected $table = 'balita_classification';

    protected $guarded = [];

    protected $hidden = ['account_id','balita_id'];

    public function balita(){
        return $this->belongsTo('App\Models\Balita', 'balita_id');
    }

    public function account(){
        return $this->belongsTo('App\Models\Account', 'account_id');
    }

}