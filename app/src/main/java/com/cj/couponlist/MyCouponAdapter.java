package com.cj.couponlist;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 写一个优惠券的Adapter中间基类
 */
public class MyCouponAdapter extends BaseCouponAdapter {

    private Activity context;
    private boolean dropFlag = false;

    public MyCouponAdapter(Activity context) {
        super(context, COUPON_PAGE_CHOOSE);
        this.context = context;
    }

    public MyCouponAdapter(Activity context, int page, String para) {
        super(context, page);
        this.context = context;
        this.para = para;
    }

    public void setBg(ViewHolder holder, int type) {
        int offset = 82; // dp, 等于上部布局的高度
        switch (type) {
            case STATUS_CAN_USE:
                setBackgroundDrawable(holder.couponDisplayView, CouponDisplayView.getBacgroudDrawable(context, "#C7A582", "#BA936B", offset));
                break;
            case STATUS_ALREADY_USE:
                setBackgroundDrawable(holder.couponDisplayView, CouponDisplayView.getBacgroudDrawable(context, "#CCCCCC", "#C3C3C3", offset));
                break;
            case STATUS_ALREADY_OVERDUE:
                setBackgroundDrawable(holder.couponDisplayView, CouponDisplayView.getBacgroudDrawable(context, "#CCCCCC", "#C3C3C3", offset));
                break;
            case STATUS_BG:
                holder.couponDisplayView.setPaintColor("#F4F4F4");
                break;
        }
    }

    public void setPic(ViewHolder holder, int type) {
        // 不设置
    }

    // 个性化控件初始化
    public void initExtraView(ViewHolder holder, View convertView) {
        holder.couponDisplayView = convertView.findViewById(R.id.couponView);
        holder.ll_top_part = convertView.findViewById(R.id.ll_top_part);
        holder.rlt_discount_ticket_bottom_part = convertView.findViewById(R.id.rlt_discount_ticket_top_part);
        holder.ticket_description = convertView.findViewById(R.id.ticket_description);
        holder.ticket_point_iv = convertView.findViewById(R.id.ticket_point_iv);
    }

    // flavor个性化绑定 -- 列表
    public void setData(BaseCoupon coupon, ViewHolder holder) {

        // 底部描述区伸缩事件
        final ViewHolder tempHolder = holder;
        holder.rlt_discount_ticket_bottom_part.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!dropFlag) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(tempHolder.ticket_point_iv, "rotation", 0f, 180f);
                    anim.setDuration(200);
                    anim.start();
                    tempHolder.ticket_description.setSingleLine(false);
                    dropFlag = true;
                } else {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(tempHolder.ticket_point_iv, "rotation", 180f, 0f);
                    anim.setDuration(200);
                    anim.start();
                    tempHolder.ticket_description.setSingleLine(true);
                    tempHolder.ticket_description.setEllipsize(TextUtils.TruncateAt.END);
                    dropFlag = false;
                }
            }
        });

        // 底部描述区内容
        if (StringUtils.isNotEmpty(coupon.getLimitInfoTxt())) {
            if (holder.ticket_description != null) {
                holder.ticket_description.setText(coupon.getLimitInfoTxt());
            }
        } else {
            if (holder.rlt_discount_ticket_bottom_part != null) {
                holder.rlt_discount_ticket_bottom_part.setVisibility(View.GONE);
            }
        }

        // 个性化设置齿轮的填充色
        setBg(holder, STATUS_BG);
    }

    // flavor个性化绑定- 选择
    public void setSelect(final int position, BaseCoupon coupon, ViewHolder holder) {

        if (coupon.isCanUse()) {
            //控制图片显示
            if (coupon.getSelected()) {
                holder.ticket_pic.setVisibility(View.VISIBLE);
                holder.ticket_pic.setImageResource(R.mipmap.coupon_selected);
            } else {
                holder.ticket_pic.setVisibility(View.GONE);
            }
            setBg(holder, STATUS_CAN_USE);
        } else {
            holder.ticket_pic.setVisibility(View.VISIBLE);
            holder.ticket_pic.setImageResource(R.mipmap.coupon_disabled);
            setBg(holder, STATUS_ALREADY_USE);
        }
        holder.ticket_action.setVisibility(View.GONE);
        // 动态设置背景
        setBg(holder, STATUS_BG);

    }

    // flavor个性化绑定
    public void setLimit(BaseCoupon coupon, ViewHolder holder) {
        holder.ticket_action.setVisibility(View.GONE);
        holder.ticket_pic.setVisibility(View.VISIBLE);
        holder.ticket_pic.setImageResource(R.mipmap.coupon_received);
        // 动态设置背景
        setBg(holder, STATUS_BG);
    }

    @SuppressWarnings("deprecation")
    public void setBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

}
