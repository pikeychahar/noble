package com.square.pos.activity_docs;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.ImageActivity;
import com.square.pos.model.DocsWallet;

public class DocViewActivity extends AbstractActivity {
    private Context mContext;
    private SharedPreferences preferences;
    private DocsWallet docsWalletObj;
    private TextView txtDocType, txtDocDes, txtDocDes1, txtInsert, btnDownload1, btnDownload2,
            btnDownload3, btnDownload4;
    private ImageView imgDoc1, imgDoc2, imgDoc3, imgDoc4;
    private String url, url1, url2, url3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        Intent intent = getIntent();
        docsWalletObj = (DocsWallet) intent.getSerializableExtra("doc");

        txtDocType = findViewById(R.id.docType);
        txtDocDes = findViewById(R.id.docDes);
        txtDocDes1 = findViewById(R.id.docDes1);
        txtInsert = findViewById(R.id.txtInsert);

        btnDownload1 = findViewById(R.id.btnDownload1);
        btnDownload2 = findViewById(R.id.btnDownload2);
        btnDownload3 = findViewById(R.id.btnDownload3);
        btnDownload4 = findViewById(R.id.btnDownload4);

        imgDoc1 = findViewById(R.id.docImage1);
        imgDoc2 = findViewById(R.id.docImage2);
        imgDoc3 = findViewById(R.id.docImage3);
        imgDoc4 = findViewById(R.id.docImage4);

        txtDocType = findViewById(R.id.docType);
        txtDocDes = findViewById(R.id.docDes);
        txtDocDes1 = findViewById(R.id.docDes1);
        txtInsert = findViewById(R.id.txtInsert);

        btnDownload1 = findViewById(R.id.btnDownload1);
        btnDownload2 = findViewById(R.id.btnDownload2);
        btnDownload3 = findViewById(R.id.btnDownload3);
        btnDownload4 = findViewById(R.id.btnDownload4);

        imgDoc1 = findViewById(R.id.docImage1);
        imgDoc2 = findViewById(R.id.docImage2);
        imgDoc3 = findViewById(R.id.docImage3);
        imgDoc4 = findViewById(R.id.docImage4);

        if (docsWalletObj != null) {
            if (docsWalletObj.getType().equalsIgnoreCase("non_motor")) {

                txtDocType.setText("Non Motor");
                txtDocDes.setText(docsWalletObj.getClientPartyName());
                txtDocDes1.setText(docsWalletObj.getClientPartyContact());

                url = docsWalletObj.getMandateDocument();
                url1 = docsWalletObj.getProposalDocument();
                url2 = docsWalletObj.getKycDocument();
                url3 = docsWalletObj.getOtherDocument();


                if (!TextUtils.isEmpty(url)) {

                    Glide.with(mContext)
                            .load(url)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url);
                        }
                    });

                    if (!TextUtils.isEmpty(url1)) {
                        Glide.with(mContext)
                                .load(url1)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url1);
                            }
                        });
                        if (!TextUtils.isEmpty(url2)) {
                            Glide.with(mContext)
                                    .load(url2)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url2);
                                }
                            });
                            if (!TextUtils.isEmpty(url3)) {

                                Glide.with(mContext)
                                        .load(url3)
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.placeholder)
                                        .animate(android.R.anim.fade_in)
                                        .into(imgDoc4);
                                imgDoc4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showImage(url3);
                                    }
                                });
                            } else findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else if (!TextUtils.isEmpty(url3)) {
                            Glide.with(mContext)
                                    .load(url3)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url3);
                                }
                            });
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.rlImage3).setVisibility(View.GONE);
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        }
                    } else if (!TextUtils.isEmpty(url2)) {
                        Glide.with(mContext)
                                .load(url2)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url2);
                            }
                        });
                        if (!TextUtils.isEmpty(url3)) {
                            Glide.with(mContext)
                                    .load(url3)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url3);
                                }
                            });
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.rlImage3).setVisibility(View.GONE);
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        }
                    } else if (!TextUtils.isEmpty(url3)) {
                        Glide.with(mContext)
                                .load(url3)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url3);
                            }
                        });
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    }


                } else if (!TextUtils.isEmpty(url1)) {
                    Glide.with(mContext)
                            .load(url1)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc2);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url1);
                        }
                    });
                    if (!TextUtils.isEmpty(url2)) {

                        Glide.with(mContext)
                                .load(url2)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url2);
                            }
                        });
                        if (!TextUtils.isEmpty(url3)) {
                            Glide.with(mContext)
                                    .load(url3)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url3);
                                }
                            });
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.rlImage3).setVisibility(View.GONE);
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        }
                    } else if (!TextUtils.isEmpty(url3)) {
                        Glide.with(mContext)
                                .load(url3)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url3);
                            }
                        });
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    }
                } else if (!TextUtils.isEmpty(url2)) {
                    Glide.with(mContext)
                            .load(url2)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url2);
                        }
                    });
                    if (!TextUtils.isEmpty(url3)) {
                        Glide.with(mContext)
                                .load(url3)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url3);
                            }
                        });
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    }
                } else if (!TextUtils.isEmpty(url3)) {
                    Glide.with(mContext)
                            .load(url3)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc2);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url3);
                        }
                    });
                    findViewById(R.id.rlImage2).setVisibility(View.GONE);
                    findViewById(R.id.rlImage3).setVisibility(View.GONE);
                    findViewById(R.id.rlImage4).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.rlImage1).setVisibility(View.GONE);
                    findViewById(R.id.rlImage2).setVisibility(View.GONE);
                    findViewById(R.id.rlImage3).setVisibility(View.GONE);
                    findViewById(R.id.rlImage4).setVisibility(View.GONE);
                }

            } else if (docsWalletObj.getType().equalsIgnoreCase("Motor")) {
                txtDocType.setText("Motor");
                txtDocDes.setText(docsWalletObj.getVehicleRegNo());
                txtDocDes1.setText(docsWalletObj.getPolicyNo());

                url = docsWalletObj.getRcFrontImage();
                url1 = docsWalletObj.getRcBackImage();
                url2 = docsWalletObj.getInsDocument();
                url3 = docsWalletObj.getOtherDocument();

                if (!TextUtils.isEmpty(url)) {
                    Glide.with(mContext)
                            .load(url)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url);
                        }
                    });

                    if (!TextUtils.isEmpty(url1)) {
                        Glide.with(mContext)
                                .load(url1)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url1);
                            }
                        });
                        if (!TextUtils.isEmpty(url2)) {
                            Glide.with(mContext)
                                    .load(url2)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url2);
                                }
                            });
                            if (!TextUtils.isEmpty(url3)) {
                                Glide.with(mContext)
                                        .load(url3)
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.placeholder)
                                        .animate(android.R.anim.fade_in)
                                        .into(imgDoc4);
                                imgDoc4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showImage(url3);
                                    }
                                });
                            } else findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else if (!TextUtils.isEmpty(url3)) {
                            Glide.with(mContext)
                                    .load(url3)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url3);
                                }
                            });
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.rlImage3).setVisibility(View.GONE);
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        }
                    } else if (!TextUtils.isEmpty(url2)) {
                        Glide.with(mContext)
                                .load(url2)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url2);
                            }
                        });
                        if (!TextUtils.isEmpty(url3)) {
                            Glide.with(mContext)
                                    .load(url3)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url3);
                                }
                            });
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.rlImage3).setVisibility(View.GONE);
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        }
                    } else if (!TextUtils.isEmpty(url3)) {
                        Glide.with(mContext)
                                .load(url3)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url3);
                            }
                        });
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    }


                } else if (!TextUtils.isEmpty(url1)) {
                    Glide.with(mContext)
                            .load(url1)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url1);
                        }
                    });
                    if (!TextUtils.isEmpty(url2)) {
                        Glide.with(mContext)
                                .load(url2)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url2);
                            }
                        });
                        if (!TextUtils.isEmpty(url3)) {
                            Glide.with(mContext)
                                    .load(url3)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(imgDoc3);
                            imgDoc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showImage(url3);
                                }
                            });
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.rlImage3).setVisibility(View.GONE);
                            findViewById(R.id.rlImage4).setVisibility(View.GONE);
                        }
                    } else if (!TextUtils.isEmpty(url3)) {
                        Glide.with(mContext)
                                .load(url3)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url3);
                            }
                        });
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    }
                } else if (!TextUtils.isEmpty(url2)) {
                    Glide.with(mContext)
                            .load(url2)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url2);
                        }
                    });
                    if (!TextUtils.isEmpty(url3)) {
                        Glide.with(mContext)
                                .load(url3)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .animate(android.R.anim.fade_in)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url3);
                            }
                        });
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    }
                } else if (!TextUtils.isEmpty(url3)) {
                    Glide.with(mContext)
                            .load(url3)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .animate(android.R.anim.fade_in)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url3);
                        }
                    });
                    findViewById(R.id.rlImage2).setVisibility(View.GONE);
                    findViewById(R.id.rlImage3).setVisibility(View.GONE);
                    findViewById(R.id.rlImage4).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.rlImage1).setVisibility(View.GONE);
                    findViewById(R.id.rlImage2).setVisibility(View.GONE);
                    findViewById(R.id.rlImage3).setVisibility(View.GONE);
                    findViewById(R.id.rlImage4).setVisibility(View.GONE);
                }

            } else {
                txtDocType.setText("Health");
                txtDocDes.setText(docsWalletObj.getClientPartyName());
                txtDocDes1.setText(docsWalletObj.getClientPartyContact());

                url = docsWalletObj.getProposalDocument();
                url1 = docsWalletObj.getKycDocument();


                if (!TextUtils.isEmpty(url)) {
                    Glide.with(this)
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url);
                        }
                    });

                    if (!TextUtils.isEmpty(url1)) {
                        Glide.with(this)
                                .load(url1)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(imgDoc2);
                        imgDoc2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImage(url1);
                            }
                        });
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);

                    } else {
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        findViewById(R.id.rlImage3).setVisibility(View.GONE);
                        findViewById(R.id.rlImage4).setVisibility(View.GONE);
                    }
                } else if (!TextUtils.isEmpty(url1)) {
                    Glide.with(this)
                            .load(url1)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgDoc1);
                    imgDoc1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImage(url1);
                        }
                    });
                    findViewById(R.id.rlImage2).setVisibility(View.GONE);
                    findViewById(R.id.rlImage3).setVisibility(View.GONE);
                    findViewById(R.id.rlImage4).setVisibility(View.GONE);
                }
            }
            txtInsert.setText(docsWalletObj.getInsertDate());


        }
        btnDownload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url))
                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        // Chrome is not installed
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                    }
            }
        });
        btnDownload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url1))
                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(url1));
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        // Chrome is not installed
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                        startActivity(i);
                    }
            }
        });
        btnDownload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url2))
                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(url2));
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        // Chrome is not installed
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                        startActivity(i);
                    }
            }
        });
        btnDownload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url3))
                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(url3));
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        // Chrome is not installed
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url3));
                        startActivity(i);
                    }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showImage(String url) {
        Intent intent1 = new Intent(mContext, ImageActivity.class);
        intent1.putExtra("img", url);
        startActivity(intent1);
    }
}
